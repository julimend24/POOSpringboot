/**
 * app.js — UniGestión MVP
 * Validaciones del lado del cliente y micro-interacciones.
 * Copiá este archivo en: src/main/resources/static/js/app.js
 */

// ============================================================
//  CONFIRM DELETE — reemplaza window.confirm nativo
//  Se llama desde los atributos onsubmit en los formularios
//  de eliminación: onsubmit="return confirmDelete(event, this)"
// ============================================================
function confirmDelete(event, form) {
    event.preventDefault();

    // Crear overlay de confirmación
    const overlay = document.createElement('div');
    overlay.className = 'confirm-overlay';
    overlay.innerHTML = `
        <div class="confirm-box" role="dialog" aria-modal="true" aria-labelledby="confirmTitle">
            <h3 id="confirmTitle">¿Eliminar registro?</h3>
            <p>Esta acción no puede deshacerse. El estudiante será eliminado permanentemente del sistema.</p>
            <div class="confirm-actions">
                <button class="btn btn-outline" id="cancelBtn">Cancelar</button>
                <button class="btn btn-danger" id="confirmBtn">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="14" height="14">
                        <polyline points="3,6 5,6 21,6"/>
                        <path d="M19,6l-1,14H6L5,6"/>
                        <path d="M10,11v6"/><path d="M14,11v6"/>
                        <path d="M9,6V4h6v2"/>
                    </svg>
                    Sí, eliminar
                </button>
            </div>
        </div>
    `;

    document.body.appendChild(overlay);

    // Cerrar con botón cancelar
    overlay.querySelector('#cancelBtn').addEventListener('click', () => {
        overlay.remove();
    });

    // Cerrar al hacer click en el fondo
    overlay.addEventListener('click', (e) => {
        if (e.target === overlay) overlay.remove();
    });

    // Confirmar: submit del formulario
    overlay.querySelector('#confirmBtn').addEventListener('click', () => {
        overlay.remove();
        form.submit();
    });

    // Cerrar con Escape
    const escHandler = (e) => {
        if (e.key === 'Escape') { overlay.remove(); document.removeEventListener('keydown', escHandler); }
    };
    document.addEventListener('keydown', escHandler);

    return false;
}

// ============================================================
//  VALIDACIÓN DEL FORMULARIO DE ESTUDIANTE (client-side)
//  Complementa la validación de Spring Boot @Valid en el server
// ============================================================
document.addEventListener('DOMContentLoaded', () => {

    const form = document.getElementById('estudianteForm');
    if (!form) return;

    // Campo DNI: solo números
    const dniInput = document.getElementById('dni');
    if (dniInput) {
        dniInput.addEventListener('input', () => {
            dniInput.value = dniInput.value.replace(/\D/g, '').slice(0, 8);
        });

        dniInput.addEventListener('blur', () => {
            const val = dniInput.value;
            clearError(dniInput);
            if (val && (val.length < 7 || val.length > 8)) {
                showError(dniInput, 'El DNI debe tener 7 u 8 dígitos.');
            }
        });
    }

    // Campo email: validación básica
    const emailInput = document.getElementById('email');
    if (emailInput) {
        emailInput.addEventListener('blur', () => {
            const val = emailInput.value.trim();
            clearError(emailInput);
            if (val && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(val)) {
                showError(emailInput, 'Ingresá un email válido.');
            }
        });
    }

    // Campos de texto requeridos: trim en blur
    ['nombre', 'apellido'].forEach(id => {
        const input = document.getElementById(id);
        if (!input) return;
        input.addEventListener('blur', () => {
            input.value = input.value.trim();
            clearError(input);
            if (!input.value) {
                showError(input, 'Este campo es obligatorio.');
            } else if (input.value.length < 2) {
                showError(input, 'Debe tener al menos 2 caracteres.');
            }
        });
    });

    // Submit: validación completa antes de enviar
    form.addEventListener('submit', (e) => {
        let valid = true;

        // Nombre y apellido requeridos
        ['nombre', 'apellido'].forEach(id => {
            const input = document.getElementById(id);
            if (!input) return;
            clearError(input);
            if (!input.value.trim()) {
                showError(input, 'Este campo es obligatorio.');
                valid = false;
            }
        });

        // DNI requerido y longitud
        if (dniInput) {
            clearError(dniInput);
            const val = dniInput.value;
            if (!val) {
                showError(dniInput, 'El DNI es obligatorio.');
                valid = false;
            } else if (val.length < 7 || val.length > 8) {
                showError(dniInput, 'El DNI debe tener 7 u 8 dígitos.');
                valid = false;
            }
        }

        if (!valid) {
            e.preventDefault();
            // Scroll al primer error
            const firstError = form.querySelector('.form-control.is-invalid');
            if (firstError) firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
        } else {
            // Deshabilitar botón para evitar doble submit
            const btn = document.getElementById('btnSubmit');
            if (btn) {
                btn.disabled = true;
                btn.textContent = 'Guardando...';
            }
        }
    });

    // ---- Helpers ----
    function showError(input, msg) {
        input.classList.add('is-invalid');
        let err = input.nextElementSibling;
        if (!err || !err.classList.contains('form-error')) {
            err = document.createElement('div');
            err.className = 'form-error';
            input.parentNode.insertBefore(err, input.nextSibling);
        }
        err.textContent = msg;
    }

    function clearError(input) {
        input.classList.remove('is-invalid');
        const next = input.nextElementSibling;
        if (next && next.classList.contains('form-error')) next.remove();
    }

});

// ============================================================
//  BÚSQUEDA EN TIEMPO REAL (debounce) en el listado
// ============================================================
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInput');
    const searchForm  = document.getElementById('searchForm');
    if (!searchInput || !searchForm) return;

    let debounceTimer;

    searchInput.addEventListener('input', () => {
        clearTimeout(debounceTimer);
        // Autosubmit después de 500ms de pausa
        debounceTimer = setTimeout(() => {
            searchForm.submit();
        }, 500);
    });

    // Limpiar con Escape
    searchInput.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') {
            searchInput.value = '';
            clearTimeout(debounceTimer);
            window.location.href = '/estudiantes';
        }
    });
});

// ============================================================
//  AUTO-DISMISS de alertas después de 5 segundos
// ============================================================
document.addEventListener('DOMContentLoaded', () => {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity .4s ease, transform .4s ease';
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-8px)';
            setTimeout(() => alert.remove(), 400);
        }, 5000);
    });
});
