<%--
  Created by IntelliJ IDEA.
  User: Amir
  Date: 2/19/2025
  Time: 8:12 AM
  To change this template use File | Settings | File Templates.
--%>

<header class="bg-white">
    <nav class="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8" aria-label="Global">
        <div class="flex lg:flex-1">
            <a href="#" class="-m-1.5 p-1.5">
                <span class="sr-only">Your Company</span>
                <img class="h-8 w-auto"
                     src="https://tailwindui.com/plus-assets/img/logos/mark.svg?color=indigo&shade=600" alt="">
            </a>
        </div>
        <div class="flex lg:hidden">
            <button type="button" class="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700">
                <span class="sr-only">Open main menu</span>
                <svg class="size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
                     aria-hidden="true" data-slot="icon">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"/>
                </svg>
            </button>
        </div>
        <div class="hidden lg:flex lg:gap-x-12">
            <div class="relative">
                <button type="button" class="flex items-center gap-x-1 text-sm/6 font-semibold text-gray-900"
                        aria-expanded="false">
                    Product
                    <svg class="size-5 flex-none text-gray-400" viewBox="0 0 20 20" fill="currentColor"
                         aria-hidden="true" data-slot="icon">
                        <path fill-rule="evenodd"
                              d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z"
                              clip-rule="evenodd"/>
                    </svg>
                </button>
            </div>

            <a href="#" class="text-sm/6 font-semibold text-gray-900">Features</a>
            <a href="#" class="text-sm/6 font-semibold text-gray-900">Marketplace</a>
            <a href="#" class="text-sm/6 font-semibold text-gray-900">Company</a>
        </div>
        <div class="hidden lg:flex lg:flex-1 lg:justify-end">
            <a href="auth/login" class="text-sm/6 font-semibold text-gray-900">Log in <span aria-hidden="true">&rarr;</span></a>
        </div>
    </nav>
    <!-- Mobile menu, show/hide based on menu open state. -->
    <div class="lg:hidden" role="dialog" aria-modal="true">
        <!-- Background backdrop, show/hide based on slide-over state. -->
        <div class="fixed inset-0 z-10"></div>
        <div class="fixed inset-y-0 right-0 z-10 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10">
            <div class="flex items-center justify-between">
                <a href="#" class="-m-1.5 p-1.5">
                    <span class="sr-only">Your Company</span>
                    <img class="h-8 w-auto"
                         src="https://tailwindui.com/plus-assets/img/logos/mark.svg?color=indigo&shade=600" alt="">
                </a>
                <button type="button" class="-m-2.5 rounded-md p-2.5 text-gray-700">
                    <span class="sr-only">Close menu</span>
                    <svg class="size-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
                         aria-hidden="true" data-slot="icon">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12"/>
                    </svg>
                </button>
            </div>
            <div class="mt-6 flow-root">
                <div class="-my-6 divide-y divide-gray-500/10">
                    <div class="space-y-2 py-6">
                        <div class="-mx-3">
                            <button type="button"
                                    class="flex w-full items-center justify-between rounded-lg py-2 pr-3.5 pl-3 text-base/7 font-semibold text-gray-900 hover:bg-gray-50"
                                    aria-controls="disclosure-1" aria-expanded="false">
                                Product
                                <!--
                                  Expand/collapse icon, toggle classes based on menu open state.

                                  Open: "rotate-180", Closed: ""
                                -->
                                <svg class="size-5 flex-none" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true"
                                     data-slot="icon">
                                    <path fill-rule="evenodd"
                                          d="M5.22 8.22a.75.75 0 0 1 1.06 0L10 11.94l3.72-3.72a.75.75 0 1 1 1.06 1.06l-4.25 4.25a.75.75 0 0 1-1.06 0L5.22 9.28a.75.75 0 0 1 0-1.06Z"
                                          clip-rule="evenodd"/>
                                </svg>
                            </button>
                            <!-- 'Product' sub-menu, show/hide based on menu state. -->

                        </div>
                        <a href="#"
                           class="-mx-3 block rounded-lg px-3 py-2 text-base/7 font-semibold text-gray-900 hover:bg-gray-50">Features</a>
                        <a href="#"
                           class="-mx-3 block rounded-lg px-3 py-2 text-base/7 font-semibold text-gray-900 hover:bg-gray-50">Marketplace</a>
                        <a href="#"
                           class="-mx-3 block rounded-lg px-3 py-2 text-base/7 font-semibold text-gray-900 hover:bg-gray-50">Company</a>
                    </div>
                    <div class="py-6">
                        <a href="/login"
                           class="-mx-3 block rounded-lg px-3 py-2.5 text-base/7 font-semibold text-gray-900 hover:bg-gray-50">Log
                            in</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
